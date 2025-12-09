from setuptools import setup, find_packages

setup(
    name='apiverve_spfvalidator',
    version='1.1.12',
    packages=find_packages(),
    include_package_data=True,
    install_requires=[
        'requests',
        'setuptools'
    ],
    description='SPF Validator checks the Sender Policy Framework (SPF) DNS record for a domain to verify if it’s valid and optionally whether a given IP address is authorized to send emails for that domain.',
    author='APIVerve',
    author_email='hello@apiverve.com',
    url='https://apiverve.com',
    classifiers=[
        'Programming Language :: Python :: 3',
        'Operating System :: OS Independent',
    ],
    python_requires='>=3.6',
)
